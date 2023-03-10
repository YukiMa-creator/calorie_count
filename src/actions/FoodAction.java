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
import services.CalorieService;
import services.FoodService;

/**
 *従業員に関わる処理を行うActionクラス
 *
 */
public class FoodAction extends ActionBase {

    private FoodService service;
    private CalorieService cservice;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new FoodService();
        cservice = new CalorieService();

        //メソッドを実行
        invoke();

        service.close();
        cservice.close();
    }

    /**
     * ログイン会員が登録したfoodの一覧画面を表示する
     */
    public void index() throws ServletException, IOException {

        // 以下追記

        //セッションからログイン中の会員情報を取得
        UserView loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_USE);

        //ログイン中の会員が作成したFOODデータを、指定されたページ数の一覧画面に表示する分取得する
        int page = getPage();
        List<FoodView> foods = service.getMinePerPage(loginUser, page);

        //ログイン中の従業員が作成したFOODデータの件数を取得
        long myFoodsCount = service.countAllMine(loginUser);

        putRequestScope(AttributeConst.FOODS, foods); //取得したFOODデータ
        putRequestScope(AttributeConst.FOD_COUNT, myFoodsCount); //ログイン中の会員が作成したFOODの数
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

            //セッションからログイン中の会員情報を取得
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

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        //idを条件にFOODデータを取得する
        FoodView fv = service.findOne(toNumber(getRequestParam(AttributeConst.FOD_ID)));

        //セッションからログイン中の会員情報を取得
        UserView uv = (UserView) getSessionScope(AttributeConst.LOGIN_USE);

        if (uv == null || uv.getId() != fv.getUser().getId()) {
            //該当のFOODデータが存在しない、またはログインしている会員がFOODの製作者でない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.TOKEN, getTokenId());//CSRF対策用トークン
            putRequestScope(AttributeConst.FOOD, fv); //取得したFOODデータ
            putSessionScope(AttributeConst.FOOD, fv);

            //編集画面を表示
            forward(ForwardConst.FW_FOD_EDIT);
        }

    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF対策 tokenチェック
        if (checkToken()) {

            //idを条件にFOODデータを取得する
            FoodView fv = service.findOne(toNumber(getRequestParam(AttributeConst.FOD_ID)));

            //入力されたFOOD内容を設定
            fv.setCode(getRequestParam(AttributeConst.FOD_CODE));
            fv.setName(getRequestParam(AttributeConst.FOD_NAME));
            fv.setAmount(getRequestParam(AttributeConst.FOD_AMOUNT));
            fv.setKcal(getRequestParam(AttributeConst.FOD_CAL));

            //FOODデータを更新する
            List<String> errors = service.update(fv);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId());
                putRequestScope(AttributeConst.FOOD, fv);
                putRequestScope(AttributeConst.ERR, errors);

                //編集画面を再表示
                forward(ForwardConst.FW_FOD_EDIT);
            } else {
                //更新中にエラーがなかった場合

                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_FOD, ForwardConst.CMD_INDEX);
            }
        }
    }

    /**
     *削除する
     */
//    public void destroy() throws ServletException, IOException {
        //CSRF対策 tokenチェック
  //      if (checkToken()) {

            //idを条件にFOODデータを取得する
    //        FoodView fv = service.findOne(toNumber(getRequestParam(AttributeConst.FOD_ID)));

      //      service.destroy(fv);

        //    putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());
          //  removeSessionScope(AttributeConst.FOOD);

            //一覧画面にリダイレクト
            //redirect(ForwardConst.ACT_FOD, ForwardConst.CMD_INDEX);
        //}
    //}

}
