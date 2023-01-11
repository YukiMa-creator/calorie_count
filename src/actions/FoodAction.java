package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.FoodView;
import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.FoodService;

/**
 *従業員に関わる処理を行うActionクラス
 *
 */
public class FoodAction extends ActionBase {

    private FoodService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new FoodService();

        //メソッドを実行
        invoke();

        service.close();
    }

    /**
     * ログイン会員が登録したfoodの一覧画面を表示する
     */
    public void index() throws ServletException, IOException {

        // 以下追記

        //セッションからログイン中の会員情報を取得
        UserView loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_USE);

        //ログイン中の会員が作成した日報データを、指定されたページ数の一覧画面に表示する分取得する
        int page = getPage();
        List<FoodView> foods = service.getMinePerPage(loginUser, page);

        //ログイン中の従業員が作成した日報データの件数を取得
        long myFoodsCount = service.countAllMine(loginUser);

        putRequestScope(AttributeConst.FOODS, foods); //取得した日報データ
        putRequestScope(AttributeConst.FOD_COUNT, myFoodsCount); //ログイン中の従業員が作成した日報の数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //↑ここまで追記

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_FOD_INDEX);
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //FOOD情報の空インスタンスを作成する

        FoodView fv = new FoodView();
        putRequestScope(AttributeConst.FOOD, fv);

        //新規登録画面を表示
        forward(ForwardConst.FW_FOD_NEW);
    }

    public void create() throws ServletException, IOException {

        //CSRF対策用トークン
        if (checkToken()) {

            //セッションからログイン中の従業員情報を取得
            UserView uv = (UserView) getSessionScope(AttributeConst.LOGIN_USE);

            //パラメータの値を元にFOOD情報のインスタンスを作成する

            FoodView fv = new FoodView(
                    null,
                    uv,
                    getRequestParam(AttributeConst.FOD_CODE),
                    getRequestParam(AttributeConst.FOD_NAME),
                    getRequestParam(AttributeConst.FOD_AMOUNT),
                    getRequestParam(AttributeConst.FOD_CAL),
                    null,
                    null);

            //FOOD情報登録
            List<String> errors = service.create(fv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.FOOD, fv);
                putRequestScope(AttributeConst.TOKEN, getTokenId());
                putRequestScope(AttributeConst.ERR, errors);

                //新規登録画面を再表示する
                forward(ForwardConst.FW_FOD_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_FOD, ForwardConst.CMD_INDEX);
            }

        }
    }
}