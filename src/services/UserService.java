package services;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.NoResultException;

import actions.views.UserConverter;
import actions.views.UserView;
import constants.JpaConst;
import models.User;
import models.validators.UserValidator;
import utils.EncryptUtil;

/**
 *従業員テーブルの操作に関わる処理を行うクラス
 */

public class UserService extends ServiceBase {

    /**
     * 会員番号、パスワードを条件に取得したデータをUserViewのインスタンスで返却する
     * @param code 会員番号
     * @param plainPass パスワード文字列
     * @param pepper pepper文字列
     * @return 取得データのインスタンス 取得できない場合null
     */
    public UserView findOne(String code, String plainPass, String pepper) {
        User u = null;
        try {
            //パスワードのハッシュ化
            String pass = EncryptUtil.getPasswordEncrypt(plainPass, pepper);

            //会員番号とハッシュ化済パスワードを条件に未削除の従業員を１件取得する
            u = em.createNamedQuery(JpaConst.Q_USE_BY_CODE_AND_PASS, User.class)
                    .setParameter(JpaConst.JPQL_PARM_CODE, code)
                    .setParameter(JpaConst.JPQL_PARM_PASSWORD, pass)
                    .getSingleResult();
        } catch (NoResultException ex) {
        }
        return UserConverter.toView(u);
    }

    /**
     * idを条件に取得したデータをUserViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public UserView findOne(int id) {
        User u = findOneInternal(id);
        return UserConverter.toView(u);
    }

    public List<String> create(UserView uv, String pepper) {

        //パスワードをハッシュ化して設定
        String pass = EncryptUtil.getPasswordEncrypt(uv.getPassword(), pepper);
        uv.setPassword(pass);

        //登録日時。更新日時は現在時刻を設定する
        LocalDateTime now = LocalDateTime.now();
        uv.setCreatedAt(now);
        uv.setUpdatedAt(now);

        //登録内容のバリテーションを行う
        List<String> errors = UserValidator.validate(this, uv, true, true);

        //バリテーションエラーがなければデータを登録する
        if (errors.size() == 0) {
            create(uv);
        }
        //エラーを返却
        return errors;
    }

    public List<String> update(UserView uv, String pepper) {

        //idを条件に登録済みの従業員情報を取得する
        UserView savedUse = findOne(uv.getId());

        boolean validateCode = false;
        if (!savedUse.getCode().equals(uv.getCode())) {

            //会員番号を更新する場合

            //会員番号についてのバリテーションを行う
            validateCode = true;
            //変更後の会員番号を設定する
            savedUse.setCode(uv.getCode());
        }

        boolean validatePass = false;
        if (uv.getPassword() != null && !uv.getPassword().equals("")) {
            //パスワードに入力がある場合

            //パスワードについてのバリテーションを行う
            validatePass = true;

            //変更後のパスワードをハッシュ化し設定する
            savedUse.setPassword(
                    EncryptUtil.getPasswordEncrypt(uv.getPassword(), pepper));
        }

        savedUse.setName(uv.getName()); //変更後の氏名を設定する
        savedUse.setMail(uv.getMail()); //変更後のメールアドレスを設定する

        //更新日時に現在時刻を設定する
        LocalDateTime today = LocalDateTime.now();
        savedUse.setUpdatedAt(today);

        //更新についてのバリテーションを行う
        List<String> errors = UserValidator.validate(this, savedUse, validateCode, validatePass);

        //バリテーションエラーがなければデータを更新する
        if (errors.size() == 0) {
            update(savedUse);
        }

        //エラーを返却（エラーがなければ０件の空リスト）
        return errors;
    }

    /**
     * idを条件に会員データを論理削除する
     * @param id
     */
    public void destroy(Integer id) {

        //idを条件に登録済みの従業員情報を取得する
        UserView savedEmp = findOne(id);

        //更新日時に現在時刻を設定する
        LocalDateTime today = LocalDateTime.now();
        savedEmp.setUpdatedAt(today);

        //論理削除フラグをたてる
        savedEmp.setDeleteFlag(JpaConst.USE_DEL_TRUE);

        //更新処理を行う
        update(savedEmp);

    }

    /**
     * 会員番号とパスワードを条件に検索し、データが取得できるかどうかで認証結果を返却する
     * @param code 会員番号
     * @param plainPass パスワード
     * @param pepper pepper文字列
     * @return 認証結果を返却する（実施する:true 失敗:false）
     */
    public Boolean validateLogin(String code, String plainPass, String pepper) {

        boolean isValidUser = false;
        if (code != null && !code.equals("") && plainPass != null && !plainPass.equals("")) {
            UserView uv = findOne(code, plainPass, pepper);

            if (uv != null && uv.getId() != null) {

                //データが取得できた場合、認証成功
                isValidUser = true;
            }
        }

        //認証結果を返却する
        return isValidUser;
    }

    /**
     * 従業員データを更新する
     * @param uv 画面から入力された従業員の登録内容
     */
    private void update(UserView uv) {
        em.getTransaction().begin();
        User u = findOneInternal(uv.getId());
        UserConverter.copyViewToModel(u, uv);
        em.getTransaction().commit();
    }

    /**
     * ユーザーデータを１件登録する
     * @param uv ユーザーデータ
     * @return 登録結果(成功:true 失敗:false)
     */
    private void create(UserView uv) {

        em.getTransaction().begin();
        em.persist(UserConverter.toModel(uv));
        em.getTransaction().commit();
    }

    /**
     * idを条件にデータを１件取得し、Userのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private User findOneInternal(int id) {
        User u = em.find(User.class, id);

        return u;
    }

    /**
     * 会員番号を条件に該当するデータの件数を取得し、返却する
     * @param code 会員番号
     * @return 該当するデータの件数
     */
    public long countByCode(String code) {

        //指定した会員番号を保持するユーザーの件数を取得する
        long users_count = (long) em.createNamedQuery(JpaConst.Q_USE_COUNT_REGISTERED_BY_CODE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_CODE, code)
                .getSingleResult();
        return users_count;
    }
}
