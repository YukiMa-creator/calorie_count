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
    String USE_COL_CODE = "code"; //食品番号
    String USE_COL_NAME = "name"; //名前
    String USE_COL_MAIL = "mail"; //メールアドレス
    String USE_COL_PASS = "password"; //パスワード
    String USE_COL_CREATE_AT = "created_at"; //登録日時
    String USE_COL_UPDATE_AT = "updated_at"; //更新日時
    String USE_COL_DELETE_FLAG = "delete_flag"; //削除フラグ

    int USE_DEL_TRUE = 1; //削除フラグON(削除済み)
    int USE_DEL_FALSE = 0; //削除フラグOFF(現役)
    //フードテーブル
    String TABLE_FOD = "foods"; //テーブル名
    //フードテーブルカラム
    String FOD_COL_ID = "id"; //id
    String FOD_COL_CODE = "code"; //会員番号
    String FOD_COL_USE = "user_id"; //foodを作成した会員のid
    String FOD_COL_NAME = "name"; //氏名
    String FOD_COL_AMOUNT = "amount"; //量
    String FOD_COL_CAL = "kcal"; //カロリー
    String FOD_COL_CREATE_AT = "created_at"; //登録日時
    String FOD_COL_UPDATE_AT = "updated_at"; //更新日時

    //カロリーテーブル
    String TABLE_CAL = "calories"; //テーブル名
  //フードテーブルカラム
    String CAL_COL_ID = "id"; //id
    String CAL_COL_USE = "user_id"; //calorieを作成した会員のid
    String CAL_COL_FOD = "food_id"; //calorieを作成したFOODのid
    String CAL_COL_CAL_DATE = "calorie_date"; //いつの日報かを示す日付
    String CAL_COL_FOD_CODE = "food_code"; //calorieを作成したfoodのcode
    String CAL_COL_CREATE_AT = "created_at"; //登録日時
    String CAL_COL_UPDATE_AT = "updated_at"; //更新日時

    //Entity名
    String ENTITY_USE = "user"; //ユーザー
    String ENTITY_CAL = "calorie"; //カロリー
    String ENTITY_FOD = "food"; //フード

    //JPQL内パラメータ
    String JPQL_PARM_CODE = "code"; //会員番号
    String JPQL_PARM_PASSWORD = "password"; //パスワード
    String JPQL_PARM_USER = "user"; //会員

    //NamedQueryのnameとquery
    //会員番号とハッシュ化済みパスワードを条件に会員を取得する
    String Q_USE_BY_CODE_AND_PASS = ENTITY_USE + ".getByCodeAndPass";
    String Q_USE_BY_CODE_AND_PASS_DEF = "SELECT u FROM User AS u WHERE u.deleteFlag = 0 AND u.code = :" + JPQL_PARM_CODE + " AND u.password = :" + JPQL_PARM_PASSWORD;
    //指定した会員番号を保持するユーザーの件数を取得する
    String Q_USE_COUNT_REGISTERED_BY_CODE = ENTITY_USE + ".countRegisteredByCode";
    String Q_USE_COUNT_REGISTERED_BY_CODE_DEF = "SELECT COUNT(u) FROM User AS u WHERE u.code = :" + JPQL_PARM_CODE;
    //指定した従業員が作成した日報を全件idの降順で取得する
    String Q_FOD_GET_ALL_MINE = ENTITY_FOD + ".getAllMine";
    String Q_FOD_GET_ALL_MINE_DEF = "SELECT f FROM Food AS f WHERE f.user = :" + JPQL_PARM_USER + " ORDER BY f.id DESC";
    //指定した会員が作成したカロリーを全件idの降順で取得する
    String Q_CAL_GET_ALL_MINE = ENTITY_CAL + ".getAllMine";
    String Q_CAL_GET_ALL_MINE_DEF = "SELECT c FROM Calorie AS c WHERE c.user = :" + JPQL_PARM_USER + " ORDER BY c.id DESC";
  //指定した従業員が作成した日報の件数を取得する
    String Q_FOD_COUNT_ALL_MINE = ENTITY_FOD + ".countAllMine";
    String Q_FOD_COUNT_ALL_MINE_DEF = "SELECT COUNT(f) FROM Food AS f WHERE f.user = :" + JPQL_PARM_USER;
  //指定した会員が作成したカロリーの件数を取得する
    String Q_CAL_COUNT_ALL_MINE = ENTITY_CAL + ".countAllMine";
    String Q_CAL_COUNT_ALL_MINE_DEF = "SELECT COUNT(c) FROM Calorie AS c WHERE c.user = :" + JPQL_PARM_USER;
    //指定した会員番号を保持するユーザーの件数を取得する
    String Q_FOD_COUNT_REGISTERED_BY_CODE = ENTITY_FOD + ".countRegisteredByCode";
    String Q_FOD_COUNT_REGISTERED_BY_CODE_DEF = "SELECT COUNT(f) FROM Food AS f WHERE f.code = :" + JPQL_PARM_CODE;

    //データ取得件数の最大値
    int ROW_PER_PAGE = 15; //1ページに表示するレコードの数
}
