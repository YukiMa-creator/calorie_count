package constants;

/**
 * 各出力メッセージを定義するEnumクラス
 *
 */
public enum MessageConst {

    //DB更新
    I_REGISTERED("登録が完了しました。"),
    I_UPDATED("更新が完了しました。"),
    I_DELETED("削除が完了しました。"),
    I_LOGINED("ログインしました。"),
    I_LOGOUT("ログアウトしました。"),
    //バリテーション
    E_NOUSE_CODE("会員番号を入力してください。"),
    E_NOUSE_FCODE("食品番号を入力してください。"),
    E_USE_CODE_EXIST("入力された会員番号は既に存在しています。"),
    E_USE_FCODE_EXIST("入力された食品番号は既に存在しています。"),
    E_NONAME("氏名を入力してください。"),
    E_FNONAME("食品を入力してください。"),
    E_NOPASSWORD("パスワードを入力してください。"),
    E_NOAMOUNT("量を入力してください。"),
    E_NOCAL("カロリーを入力してください。"),
    E_NOMAIL("メールアドレスを入力してください。");
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
