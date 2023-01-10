package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.FoodView;
import constants.MessageConst;
import services.FoodService;

/**
 *食べ物インスタンスに設定されている値のバリテーションを行うクラス
 *
 */
public class FoodValidator {

    /**
     * 食べ物インスタンスの各項目についてバリテーションを行う
     * @param fv 食べ物のインスタンス
     * @param codeDuplicateCheckFlag 会員番号の重複チェックを実施するかどうか（実施する:true 実施しない:false）
     * @return エラーのリスト
     */
    public static List<String> validate(FoodService service, FoodView fv, Boolean codeDuplicateCheckFlag) {
        List<String> errors = new ArrayList<String>();

        //食品番号をチェック
        String codeError = validateCode(service, fv.getCode(), codeDuplicateCheckFlag);
        if (!codeError.equals("")) {
            errors.add(codeError);
        }

      //食べ物のチェック
        String nameError = validateName(fv.getName());
        if (!nameError.equals("")) {
            errors.add(nameError);
        }

      //量のチェック
        String amountError = validateAmount(fv.getAmount());
        if (!amountError.equals("")) {
            errors.add(amountError);
        }

      //カロリーのチェック
        String calError = validateCal(fv.getCal());
        if (!calError.equals("")) {
            errors.add(calError);
        }

    return errors;

    }

    /**
     * 食品番号の入力チェックを行い、エラーメッセージを返却
     * @param service UserServiceのインスタンス
     * @param code 会員番号
     * @param codeDuplicateCheckFlag 会員番号の重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーメッセージ
     */
    public static String validateCode(FoodService service, String code, Boolean codeDuplicateCheckFlag) {

        //入力値がなければエラーメッセージを返却
        if (code == null || code.equals("")) {
            return MessageConst.E_NOUSE_FCODE.getMessage();
        }

        if (codeDuplicateCheckFlag) {
            //食品番号の重複チェックを実施

            long foodCount = isDuplicateUser(service, code);

            //同一会員番号が既に登録されている場合はエラーメッセージを返却
            if (foodCount > 0) {
                return MessageConst.E_USE_FCODE_EXIST.getMessage();
            }
        }

        //エラーがない場合は空文字を返却
        return "";
    }

    /**
     * @param service FoodServiceのインスタンス
     * @param code 食品番号
     * @return 会員テーブルに登録されている同一食品番号のデータの件数
     */
    public static long isDuplicateUser(FoodService service, String code) {

        long foodCount = service.countByCode(code);
        return foodCount;
    }

    /**
     * 食べ物に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param name 氏名
     * @return エラーメッセージ
     */
    private static String validateName(String name) {

        if (name == null || name.equals("")) {
            return MessageConst.E_FNONAME.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * 量に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param mail メールアドレス
     * @return エラーメッセージ
     */
    private static String validateAmount(String amount) {

        if (amount == null || amount.equals("")) {
            return MessageConst.E_NOAMOUNT.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * カロリーに入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param mail メールアドレス
     * @return エラーメッセージ
     */
    private static String validateCal(Integer cal) {

        if (cal == null || cal.equals("")) {
            return MessageConst.E_NOCAL.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }
}
