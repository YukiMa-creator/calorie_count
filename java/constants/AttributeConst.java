package constants;

/**
 *
 *画面の項目値などを定義するEnumクラス
 */
public enum AttributeConst {

    //一覧画面
    PAGE("page"),

    //入力フォーム共通
    TOKEN("_token"),

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
