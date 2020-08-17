package models.validator;

import java.util.ArrayList;
import java.util.List;

public class NewpaperValidator {

    public static List<String> validate(String b) {
        List<String> errors = new ArrayList<String>();


        String volumn_error = _validateVo(b);
        if(!volumn_error.equals("")) {
            errors.add(volumn_error);
        }

        return errors;
    }



    private static String _validateVo(String vo) {
        if (vo==null || vo.equals("")) {
            return "発行部数を入力してください";
        } else {
            Integer voi=Integer.parseInt(vo);
            if (voi < 2526) {
                return "発行部数は2526部以上にしてください";
            }
        }
        return "";
    }



}
