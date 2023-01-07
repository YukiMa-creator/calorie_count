package constants;

/**
 * DB関連の項目値を定義するインターフェース
 * ※インターフェイスに定義した変数は public static final 修飾子がついているとみなされる
 */
public interface JpaConst {

    //persistence-unit名
    String PERSISTENCE_UNIT_NAME = "calorie_count";

    //ユーザーテーブル
    String TABLE_USE = "users"; //テーブル名
    //ユーザーテーブルカラム
    String USE_COL_ID = "id"; //id
    String USE_COL_CODE = "code"; //会員番号
    String USE_COL_NAME = "name"; //氏名
    String USE_COL_MAIL = "mail"; //メールアドレス
    String USE_COL_PASS = "password"; //パスワード

    String USE_COL_CREATE_AT = "created_at"; //登録日時
    String USE_COL_UPDATE_AT = "updated_at"; //更新日時

    //Entity名
    String ENTITY_USE = "user"; //ユーザー
    String ENTITY_CAL = "calorie"; //カロリー
    String ENTITY_FOD = "food"; //フード

    //JPQL内パラメータ
    String JPQL_PARM_CODE = "code"; //会員番号
    String JPQL_PARM_PASSWORD = "password"; //パスワード


    //NamedQueryのnameとquery
    //会員番号とハッシュ化済みパスワードを条件に未削除の従業員を取得する
    String Q_USE_BY_CODE_AND_PASS = ENTITY_USE + ".getByCodeAndPass";
    String Q_USE_BY_CODE_AND_PASS_DEF = "SELECT u FROM User AS u WHERE u.code = : " + JPQL_PARM_CODE + " AND u.password = : " + JPQL_PARM_PASSWORD;

}
