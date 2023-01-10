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
 *foodデータのDTOモデル
 */

@Table(name = JpaConst.TABLE_FOD)

@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_FOD_GET_ALL_MINE,
            query = JpaConst.Q_FOD_GET_ALL_MINE_DEF),
    @NamedQuery(
            name = JpaConst.Q_FOD_COUNT_ALL_MINE,
            query = JpaConst.Q_FOD_COUNT_ALL_MINE_DEF),
})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Food {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.FOD_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     *foodを登録した会員
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.FOD_COL_USE, nullable = false)
    private User user;

    /**
     * 食品番号
     */
    @Column(name = JpaConst.FOD_COL_CODE, nullable = false, unique = true)
    private String code;

    /**
     * 食べ物
     */
    @Column(name = JpaConst.FOD_COL_NAME, length = 60, nullable = false)
    private String name;

    /**
     * 量（グラム）
     */
    @Column(name = JpaConst.FOD_COL_AMOUNT, length = 60, nullable = false)
    private String amount;

    /**
     * カロリー
     */
    @Column(name = JpaConst.FOD_COL_CAL, nullable = false)
    private Integer cal;

    /**
     * 登録日時
     */
    @Column(name = JpaConst.FOD_COL_CREATE_AT, nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = JpaConst.FOD_COL_UPDATE_AT, nullable = false)
    private LocalDateTime updatedAt;
}
