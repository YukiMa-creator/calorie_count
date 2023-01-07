package constants;

/**
 * 各出力メッセージを定義するEnumクラス
 *
 */
public enum MessageConst {

    //DB更新
    I_REGISTERED("登録が完了しました。"),
    I_UPDATED("更新が完了しました。"),

    //バリテーション
    E_NOUSE_CODE("会員番号を入力してください。"),
    E_USE_CODE_EXIST("入力された会員番号は既に存在しています。"),
    E_NONAME("氏名を入力してください。"),
    E_NOPASSWORD("パスワードを入力してください。"),
    E_NOMAIL("メールアドレスを入力してください。"),
    /**
     * 文字列
     */
 private final String text;

    /**
     * コンストラクタ
     */
    private MessageConst(final String text) {
        this.text = text;
    }

    /**
     * 値（文字列）取得
     */
    public String getMessage() {
        return this.text;
    }
}
