package constants;

/**
 *
 *リクエストパラメーターの変数名、変数値、jspファイルの名前等画面遷移に関わる値を定義するEnumクラス
 */
public enum ForwardConst {

    //action
    ACT("action"),
    ACT_TOP("Top"),
    ACT_USE("User"),
    ACT_AUTH("Auth"),
    ACT_CAL("Calorie"),
    ACT_FOD("Food"),

    //command
    CMD("command"),
    CMD_INDEX("index"),
    CMD_CREATE("create"),
    CMD_EDIT("edit"),
    CMD_NEW("entryNew"),
    CMD_UPDATE("update"),
    CMD_SHOW("show"),
    CMD_DESTROY("destroy"),
    CMD_LOGIN("login"),
    CMD_SHOW_LOGIN("showLogin"),
    CMD_LOGOUT("logout"),

    //jsp
    FW_ERR_UNKNOWN("error/unknown"),
    FW_USE_NEW("users/new"),
    FW_USE_SHOW("users/show"),
    FW_USE_EDIT("users/edit"),
    FW_FOD_EDIT("foods/edit"),
    FW_TOP_INDEX("topPage/index"),
    FW_FOD_INDEX("foods/index"),
    FW_FOD_NEW("foods/new"),
    FW_CAL_NEW("calories/new"),
    FW_CAL_SHOW("calories/show"),
    FW_LOGIN("login/login");

    /**
     * 文字列
     */
    private final String text;

    /**
     * コンストラクタ
     */
    private ForwardConst(final String text) {
        this.text = text;
    }

    /**
     * 値（文字列）取得
     */
public String getValue() {
    return this.text;
}
}
