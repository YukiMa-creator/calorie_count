package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * calorieデータのDTOモデル
 */

@Table(name = JpaConst.TABLE_CAL)

@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_CAL_GET_ALL_MINE,
            query = JpaConst.Q_CAL_GET_ALL_MINE_DEF),
    @NamedQuery(
            name = JpaConst.Q_CAL_COUNT_ALL_MINE,
            query = JpaConst.Q_CAL_COUNT_ALL_MINE_DEF),
})


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Calorie {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.CAL_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * いつの日報かを示す日付
     */
    @Column(name = JpaConst.CAL_COL_CAL_DATE, nullable = false)
    private LocalDateTime calorieDate;

    /**
     * calorieを登録した会員
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.CAL_COL_USE, nullable = false)
    private User user;

    /**
     * FOODを登録したcalorie
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.CAL_COL_FOD, nullable = false)
    private Food food;

    /**
     * 登録日時
     */
    @Column(name = JpaConst.CAL_COL_CREATE_AT, nullable = false)
    private LocalDateTime createdAt;

}
