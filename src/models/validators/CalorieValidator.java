package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.CalorieView;
import actions.views.FoodView;
import constants.MessageConst;

/**
 *カロリーインスタンスに設定されている値のバリテーションを行うクラス
 *
 */
public class CalorieValidator {

    /**
     * 食べ物インスタンスの各項目についてバリテーションを行う
     * @param cv カロリーのインスタンス
     * @return エラーのリスト
     */
    public static List<String> validate(CalorieView cv) {
        List<String> errors = new ArrayList<String>();

      //食べ物のチェック
        String foodError = validateFood(cv.getFood());
        if (!foodError.equals("")) {
            errors.add(foodError);
        }
    return errors;

    }

    /**
     * 食べ物に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param name 氏名
     * @return エラーメッセージ
     */
    private static String validateFood(FoodView food) {

        if (food == null || food.equals("")) {
            return MessageConst.E_FNOFOOD.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }
}
