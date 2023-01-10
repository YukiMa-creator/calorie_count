package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.FoodConverter;
import actions.views.FoodView;
import actions.views.UserConverter;
import actions.views.UserView;
import constants.JpaConst;
import models.Food;
import models.validators.FoodValidator;

/**
 *従業員テーブルの操作に関わる処理を行うクラス
 */

public class FoodService extends ServiceBase {

    /**
     * 指定した会員が作成したfoodデータを、指定されたページ数の一覧画面に表示する分取得しFoodViewのリストで返却する
     * @param user 従業員
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<FoodView> getMinePerPage(UserView user, int page) {

        List<Food> foods = em.createNamedQuery(JpaConst.Q_FOD_GET_ALL_MINE, Food.class)
                .setParameter(JpaConst.JPQL_PARM_USER, UserConverter.toModel(user))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return FoodConverter.toViewList(foods);
    }

    /**
     * 指定した会員が作成したfoodデータの件数を取得し、返却する
     * @param user
     * @return foodデータの件数
     */
    public long countAllMine(UserView user) {

        long count = (long) em.createNamedQuery(JpaConst.Q_FOD_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_USER, UserConverter.toModel(user))
                .getSingleResult();

        return count;
    }

    /**
     * idを条件に取得したデータをFoodViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public FoodView findOne(int id) {
        return FoodConverter.toView(findOneInternal(id));
    }

    /**
     * 画面から入力されたfoodの登録内容を元にデータを１件作成しfoodテーブルに登録する
     * @param fv
     * @return バリデーションで発生したエラーのリスト
     */

    public List<String> create(FoodView fv) {

        //登録日時。更新日時は現在時刻を設定する
        LocalDateTime now = LocalDateTime.now();
        fv.setCreatedAt(now);
        fv.setUpdatedAt(now);

        //登録内容のバリテーションを行う
        List<String> errors = FoodValidator.validate(this, fv, true);

        //バリテーションエラーがなければデータを登録する
        if (errors.size() == 0) {
            createInternal(fv);
        }
        //エラーを返却
        return errors;
    }

    /**
     * 画面から入力されたfoodの登録内容を元に、foodデータを更新する
     * @param uv
     * @return
     */
    public List<String> update(FoodView fv) {

        //idを条件に登録済みの従業員情報を取得する
        FoodView savedFood = findOne(fv.getId());

        boolean validateCode = false;
        if (!savedFood.getCode().equals(fv.getCode())) {

            //会員番号を更新する場合

            //会員番号についてのバリテーションを行う
            validateCode = true;
            //変更後の会員番号を設定する
            savedFood.setCode(fv.getCode());
        }

        savedFood.setName(fv.getName()); //変更後の食べ物を設定する
        savedFood.setAmount(fv.getAmount()); //変更後の量を設定する

        //更新日時に現在時刻を設定する
        LocalDateTime today = LocalDateTime.now();
        savedFood.setUpdatedAt(today);

        //更新についてのバリテーションを行う
        List<String> errors = FoodValidator.validate(this, savedFood, validateCode);

        //バリテーションエラーがなければデータを更新する
        if (errors.size() == 0) {
            updateInternal(savedFood);
        }

        //エラーを返却（エラーがなければ０件の空リスト）
        return errors;
    }

    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Food findOneInternal(int id) {
        return em.find(Food.class, id);
    }

    /**
     * foodデータを1件登録する
     * @param fv 日報データ
     */
    private void createInternal(FoodView fv) {

        em.getTransaction().begin();
        em.persist(FoodConverter.toModel(fv));
        em.getTransaction().commit();

    }

    /**
     * foodデータを更新する
     * @param fv 日報データ
     */
    private void updateInternal(FoodView fv) {

        em.getTransaction().begin();
        Food f = findOneInternal(fv.getId());
        FoodConverter.copyViewToModel(f, fv);
        em.getTransaction().commit();
    }

    /**
     * 会員番号を条件に該当するデータの件数を取得し、返却する
     * @param code 会員番号
     * @return 該当するデータの件数
     */
    public long countByCode(String code) {

        //指定した会員番号を保持するユーザーの件数を取得する
        long foods_count = (long) em.createNamedQuery(JpaConst.Q_FOD_COUNT_REGISTERED_BY_CODE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_CODE, code)
                .getSingleResult();
        return foods_count;
    }
}