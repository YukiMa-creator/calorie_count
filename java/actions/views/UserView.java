package actions.views;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *従業員情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter //全てのクラスフィールドについてgetterを自動生成する
@Setter //全てのクラスフィールドについてsetterを自動生成する
@NoArgsConstructor //引数なしコンストラクタを自動生成する
@AllArgsConstructor //全てのクラスフィールドを引数に持つ引数ありコンストラクタを自動生成する
public class UserView {

    /**
     * id
     */
    private Integer id;

    /**
     * 会員番号
     */
    private String code;

    /**
     * 氏名
     */
    private String name;

    /**
     * メールアドレス
     */
    private String mail;

    /**
     * パスワード
     */
    private String password;

    /**
     *登録日時
     */
    private  LocalDateTime createdAt;

    /**
     * 更新日時
     */
    private LocalDateTime updatedAt;

}
