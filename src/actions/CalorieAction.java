package actions;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.CalorieView;
import actions.views.FoodView;
import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import services.CalorieService;
import services.FoodService;

public class CalorieAction extends ActionBase {

    /**
     * カロリーに関する処理を行うActionクラス
     */

    private CalorieService service;
    private FoodService fservice;

    /**
     * メソッドを実行する
     */

    @Override
    public void process() throws ServletException, IOException {

        service = new CalorieService();
        fservice = new FoodService();

        //メソッドを実行
        invoke();
        service.close();
        fservice.close();
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //日報情報の空インスタンスに、日報の日付＝今日の日付を設定する
        CalorieView uv = new CalorieView();
        uv.setCalorieDate(LocalDateTime.now());
        putRequestScope(AttributeConst.CALORIE, uv); //日付のみ設定済みの日報インスタンス

        //新規登録画面を表示
        forward(ForwardConst.FW_CAL_NEW);
    }
    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //日報の日付が入力されていなければ、今日の日付を設定
            LocalDateTime day = null;
            if (getRequestParam(AttributeConst.CAL_DATE) == null
                    || getRequestParam(AttributeConst.CAL_DATE).equals("")) {
                day = LocalDateTime.now();
            } else {
                day = LocalDateTime.parse(getRequestParam(AttributeConst.CAL_DATE));
            }

          //セッションからログイン中の従業員情報を取得
            UserView uv = (UserView) getSessionScope(AttributeConst.LOGIN_USE);

          //セッションからFOOD情報を取得
            FoodView fv = (FoodView) getSessionScope(AttributeConst.FOOD);

            //パラメータの値をもとに日報情報のインスタンスを作成する
            CalorieView cv = new CalorieView(
                    null,
                    day,
                    uv, //ログインしている従業員を、日報作成者として登録する
                    fv, //FOODを登録する
                    null,
                    null);

            //日報情報登録
            List<String> errors = service.create(cv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.CALORIE, cv);//入力された日報情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_CAL_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_TOP, ForwardConst.CMD_INDEX);
            }
        }
    }
}
