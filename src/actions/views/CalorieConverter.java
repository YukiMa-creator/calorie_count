package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Calorie;

/**
 *食べ物データのDTOモデル⇄Viewモデルの変換を行うクラス
 *
 */

public class CalorieConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param cv CalorieViewのインスタンス
     * @param Calorieのインスタンス
     */
    public static Calorie toModel(CalorieView cv) {

        return new Calorie(
                cv.getId(),
                cv.getCalorieDate(),
                UserConverter.toModel(cv.getUser()),
                FoodConverter.toModel(cv.getFood()),
                cv.getCreatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param c Calorieのインスタンス
     * @param CalorieViewのインスタンス
     */
    public static CalorieView toView(Calorie c) {

        if (c == null) {
            return null;
        }

        return new CalorieView(
                c.getId(),
                c.getCalorieDate(),
                UserConverter.toView(c.getUser()),
                FoodConverter.toView(c.getFood()),
                c.getCreatedAt());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<CalorieView> toViewList(List<Calorie> list) {
        List<CalorieView> cvs = new ArrayList<>();

        for (Calorie c : list) {
            cvs.add(toView(c));
        }
        return cvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param c DTOモデル（コピー先）
     * @param cv Viewモデル（コピー元）
     */
    public static void copyViewToModel(Calorie c, CalorieView cv) {
        c.setId(cv.getId());
        c.setCalorieDate(cv.getCalorieDate());
        c.setUser(UserConverter.toModel(cv.getUser()));
        c.setFood(FoodConverter.toModel(cv.getFood()));
        c.setCreatedAt(cv.getCreatedAt());
    }
}
