package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.User;

/**
 *従業員データのDTOモデル⇄Viewモデルの変換を行うクラス
 *
 */

public class UserConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param uv UserViewのインスタンス
     * @param Userのインスタンス
     */
    public static User toModel(UserView uv) {

        return new User(
                uv.getId(),
                uv.getCode(),
                uv.getName(),
                uv.getMail(),
                uv.getPassword(),
                uv.getCreatedAt(),
                uv.getUpdatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param u Userのインスタンス
     * @param UserViewのインスタンス
     */
    public static UserView toView(User u) {

        if(u == null) {
            return null;
        }

        return new UserView(
                u.getId(),
                u.getCode(),
                u.getName(),
                u.getMail(),
                u.getPassword(),
                u.getCreatedAt(),
                u.getUpdatedAt());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<UserView> toViewList(List<User>list){
        List<UserView> uvs = new ArrayList<>();

        for(User u : list) {
            uvs.add(toView(u));
        }
        return uvs;
    }

/**
 * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
 * @param u DTOモデル（コピー先）
 * @param uv Viewモデル（コピー元）
 */
    public static void copyViewToModel(User u, UserView uv) {
        u.setId(uv.getId());
        u.setCode(uv.getCode());
        u.setName(uv.getName());
        u.setMail(uv.getMail());
        u.setPassword(uv.getPassword());
        u.setCreatedAt(uv.getCreatedAt());
        u.setUpdatedAt(uv.getUpdatedAt());
    }
}
