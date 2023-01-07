package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.UserView;
import constants.MessageConst;
import services.UserService;

/**
 *従業員インスタンスに設定されている値のバリテーションを行うクラス
 *
 */
public class UserValidator {

    /**
     * 従業員インスタンスの各項目についてバリテーションを行う
     * @param service 呼び出し元Serviceクラスのインスタンス
     * @param uv UserViewのインスタンス
     * @param codeDuplicateCheckFlag 会員番号の重複チェックを実施するかどうか（実施する:true 実施しない:false）
     * @param passwordCheckFlag パスワードの入力チェックを実施するかどうか（実施する:true 実施しない:false）
     * @return エラーのリスト
     */
    public static List<String> validate(UserService service, UserView uv, Boolean codeDuplicateCheckFlag,
            Boolean passwordCheckFlag) {
        List<String> errors = new ArrayList<String>();

        //会員番号をチェック
        String codeError = validateCode(service, uv.getCode(), codeDuplicateCheckFlag);
        if (!codeError.equals("")) {
            errors.add(codeError);
        }

      //氏名のチェック
        String nameError = validateName(uv.getName());
        if (!nameError.equals("")) {
            errors.add(nameError);
        }

      //メールアドレスのチェック
        String mailError = validateMail(uv.getMail());
        if (!mailError.equals("")) {
            errors.add(mailError);
        }

        //パスワードのチェック
        String passwordError = validatePassword(uv.getPassword(), passwordCheckFlag);
    if(!passwordError.equals("")) {
        errors.add(passwordError);
    }

    return errors;

    }

    /**
     * 会員番号の入力チェックを行い、エラーメッセージを返却
     * @param service UserServiceのインスタンス
     * @param code 会員番号
     * @param codeDuplicateCheckFlag 会員番号の重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーメッセージ
     */
    public static String validateCode(UserService service, String code, Boolean codeDuplicateCheckFlag) {

        //入力値がなければエラーメッセージを返却
        if(code == null || code.equals("")) {
            return MessageConst.E_NOUSE_CODE.getMessage();
        }

        if(codeDuplicateCheckFlag) {
            //会員番号の重複チェックを実施

            long usersCount = isDuplicateUser(service, code);

            //同一会員番号が既に登録されている場合はエラーメッセージを返却
            if(usersCount > 0) {
                return MessageConst.E_USE_CODE_EXIST.getMessage();
            }
        }

        //エラーがない場合は空文字を返却
        return "";
    }

    /**
     * @param service UserServiceのインスタンス
     * @param code 会員番号
     * @return 従業員テーブルに登録されている同一会員番号のデータの件数
     */
    public static long isDuplicateUser(UserService service, String code) {

        long usersCount = service.countByCode(code);
        return usersCount;
    }

    /**
     * 氏名に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param name 氏名
     * @return エラーメッセージ
     */
    private static String validateName(String name) {

        if(name == null || name.equals("")) {
            return MessageConst.E_NONAME.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * メールアドレスに入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param mail メールアドレス
     * @return エラーメッセージ
     */
    private static String validateMail(String mail) {

        if(mail == null || mail.equals("")) {
            return MessageConst.E_NOMAIL.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * パスワードの入力チェックを行い、エラーメッセージを返却
     * @param password パスワード
     * @param passwordCheckFlag パスワードの入力チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーメッセージ
     */
    private static String validatePassword(String password, Boolean passwordCheckFlag) {

        //入力チェックを実施かつ入力値がなければエラーメッセージを返却
        if(passwordCheckFlag && (password == null || password.equals(""))) {
            return MessageConst.E_NOPASSWORD.getMessage();
        }

        //エラーがない場合は空文字を返却
        return "";
    }
}
