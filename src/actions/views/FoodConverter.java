package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Food;

/**
 *食べ物データのDTOモデル⇄Viewモデルの変換を行うクラス
 *
 */

public class FoodConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param fv FoodViewのインスタンス
     * @param Foodのインスタンス
     */
    public static Food toModel(FoodView fv) {

        return new Food(
                fv.getId(),
                UserConverter.toModel(fv.getUser()),
                fv.getCode(),
                fv.getName(),
                fv.getAmount(),
                fv.getKcal(),
                fv.getCreatedAt(),
                fv.getUpdatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param f Foodのインスタンス
     * @param FoodViewのインスタンス
     */
    public static FoodView toView(Food f) {

        if (f == null) {
            return null;
        }

        return new FoodView(
                f.getId(),
                UserConverter.toView(f.getUser()),
                f.getCode(),
                f.getName(),
                f.getAmount(),
                f.getKcal(),
                f.getCreatedAt(),
                f.getUpdatedAt());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<FoodView> toViewList(List<Food> list) {
        List<FoodView> fvs = new ArrayList<>();

        for (Food f : list) {
            fvs.add(toView(f));
        }
        return fvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param f DTOモデル（コピー先）
     * @param fv Viewモデル（コピー元）
     */
    public static void copyViewToModel(Food f, FoodView fv) {
        f.setId(fv.getId());
        f.setUser(UserConverter.toModel(fv.getUser()));
        f.setCode(fv.getCode());
        f.setName(fv.getName());
        f.setAmount(fv.getAmount());
        f.setKcal(fv.getKcal());
        f.setCreatedAt(fv.getCreatedAt());
        f.setUpdatedAt(fv.getUpdatedAt());
    }
}
