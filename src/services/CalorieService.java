package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.CalorieConverter;
import actions.views.CalorieView;
import actions.views.UserConverter;
import actions.views.UserView;
import constants.JpaConst;
import models.Calorie;
import models.validators.CalorieValidator;

/**
 *従業員テーブルの操作に関わる処理を行うクラス
 */

public class CalorieService extends ServiceBase {

    /**
     * 指定した会員が作成したカロリーデータを、指定されたページ数の一覧画面に表示する分取得しReportViewのリストで返却する
     * @param user 会員
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<CalorieView> getMinePerPage(UserView user, int page) {

        List<Calorie> calories = em.createNamedQuery(JpaConst.Q_CAL_GET_ALL_MINE, Calorie.class)
                .setParameter(JpaConst.JPQL_PARM_USER, UserConverter.toModel(user))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return CalorieConverter.toViewList(calories);
    }

    /**
     * 指定した会員が作成したカロリーデータの件数を取得し、返却する
     * @param user
     * @return カロリーデータの件数
     */
    public long countAllMine(UserView user) {

        long count = (long) em.createNamedQuery(JpaConst.Q_CAL_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_USER, UserConverter.toModel(user))
                .getSingleResult();

        return count;
    }

    /**
     * idを条件に取得したデータをCalorieViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public CalorieView findOne(int id) {
        return CalorieConverter.toView(findOneInternal(id));
    }

    /**
     * 画面から入力されたカロリーの登録内容を元にデータを1件作成し、カロリーテーブルに登録する
     * @param cv カロリーの登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(CalorieView cv) {
        List<String> errors = CalorieValidator.validate(cv);
        if (errors.size() == 0) {
            LocalDateTime ldt = LocalDateTime.now();
            cv.setCreatedAt(ldt);
            createInternal(cv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }


    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Calorie findOneInternal(int id) {
        return em.find(Calorie.class, id);
    }

    /**
     * カロリーデータを1件登録する
     * @param cv カロリーデータ
     */
    private void createInternal(CalorieView cv) {

        em.getTransaction().begin();
        em.persist(CalorieConverter.toModel(cv));
        em.getTransaction().commit();

    }


    /**
     * idを条件にcalorieデータを削除する
     * @param id
     */
    public CalorieView destroy(CalorieView cv) {

        em.getTransaction().begin();
      //idを条件に登録済みの従業員情報を取得する
        Calorie c = findOneInternal(cv.getId());
        em.remove(c); //データ削除
        em.getTransaction().commit();
        return cv;

    }

}