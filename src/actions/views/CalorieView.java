package actions.views;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *カロリー情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter //全てのクラスフィールドについてgetterを自動生成する
@Setter //全てのクラスフィールドについてsetterを自動生成する
@NoArgsConstructor //引数なしコンストラクタを自動生成する
@AllArgsConstructor
public class CalorieView {

    /**
     * id
     */
    private Integer id;


    /**
     * いつの日報かを示す日付
     */
    private LocalDateTime calorieDate;


    /**
     * calorieを登録した会員
     */
    private UserView user;

    /**
     * FOODを登録したcalorie
     */
    private FoodView food;

}
