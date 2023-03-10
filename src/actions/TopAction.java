package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.CalorieView;
import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.CalorieService;

/**
 * トップページに関する処理を行うActionクラス
 *
 */

public class TopAction extends ActionBase {

    private CalorieService service; //追記


    /**
     * indexメソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new CalorieService(); //追記

        //メソッドを実行
        invoke();

        service.close(); //追記

    }

    /**
     * 一覧画面を表示する
     */
    public void index() throws ServletException, IOException {

     // 以下追記

        //セッションからログイン中の従業員情報を取得
        UserView loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_USE);

        //ログイン中の従業員が作成した日報データを、指定されたページ数の一覧画面に表示する分取得する
        int page = getPage();
        List<CalorieView> calories = service.getMinePerPage(loginUser, page);

        //ログイン中の従業員が作成した日報データの件数を取得
        long myCaloriesCount = service.countAllMine(loginUser);

        putRequestScope(AttributeConst.CALORIES, calories); //取得したカロリーデータ
        putRequestScope(AttributeConst.CAL_COUNT, myCaloriesCount); //ログイン中の会員が作成したカロリーの数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //↑ここまで追記

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに差し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);

        }
        //一覧画面を表示
        forward(ForwardConst.FW_TOP_INDEX);
    }
}
