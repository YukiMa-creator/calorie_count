package constants;

/**
 *
 *画面の項目値などを定義するEnumクラス
 */
public enum AttributeConst {

    //フラッシュメッセージ
    FLUSH("flush"),

    //一覧画面
    MAX_ROW("maxRow"),
    PAGE("page"),

    //入力フォーム共通
    TOKEN("_token"),
    ERR("errors"),

    //ログイン中の会員
    LOGIN_USE("login_user"),

    //ログイン画面
    LOGIN_ERR("loginError"),

    //ユーザー管理
    USER("user"),
    USE_CODE("code"),
    USE_NAME("name"),
    USE_MAIL("mail"),
    USE_PASS("password"),
    USE_ID("id"),

    //削除フラグ
    DEL_FLAG_TRUE(1),
    DEL_FLAG_FALSE(0),

  //フード管理
    FOOD("food"),
    FOD_CODE("code"),
    FOD_NAME("name"),
    FOD_AMOUNT("amount"),
    FOD_CAL("kcal"),
    FOD_ID("id"),
    FOODS("foods"),
    FOD_COUNT("foods_count"),

    //カロリー管理
    CALORIE("calorie"),
    CAL_ID("id"),
    CAL_FOD_ID("food_id"),
    CAL_FOD("food"),
    CAL_DATE("calorie_date"),
    CALORIES("calories"),
    CAL_COUNT("calories_count"),

    ;



    private final String text;
    private final Integer i;

    private AttributeConst(final String text) {
        this.text = text;
        this.i = null;
    }

    private AttributeConst(final Integer i) {
        this.text = null;
        this.i = i;
    }

    public String getValue() {
        return this.text;

    }

    public Integer getIntegerValue() {
        return this.i;
    }
}
