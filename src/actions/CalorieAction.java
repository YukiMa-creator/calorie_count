package actions;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;

import actions.views.CalorieView;
import constants.AttributeConst;
import constants.ForwardConst;
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
}
