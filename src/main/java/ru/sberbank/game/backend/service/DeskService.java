package ru.sberbank.game.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sberbank.game.backend.persistence.DeskRepository;
import ru.sberbank.game.backend.persistence.entity.Desk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class DeskService {

    private DeskRepository deskRepository;

    public Desk getDesk(long sessionId) {
        return deskRepository.findBySessionId(sessionId);
    }

    public String drawField(Desk desk) {

        ArrayList<List> result = new ArrayList<>();

        result.add(Arrays.asList(desk.getF1(), desk.getF2(),desk.getF3()));
        result.add(Arrays.asList(desk.getF4(), desk.getF5(),desk.getF6()));
        result.add(Arrays.asList(desk.getF7(), desk.getF8(),desk.getF9()));

        return result.toString();
    }

    public String getFieldValue(Desk desk, int fieldNum) {
        switch (fieldNum) {
            case 1:
                return desk.getF1();
            case 2:
                return desk.getF2();
            case 3:
                return desk.getF3();
            case 4:
                return desk.getF4();
            case 5:
                return desk.getF5();
            case 6:
                return desk.getF6();
            case 7:
                return desk.getF7();
            case 8:
                return desk.getF8();
            default:
                return desk.getF9();
        }
    }

    public void setFieldValue(Desk desk, int fieldNum, String sign) {

        switch (fieldNum) {
            case 1:
                desk.setF1(sign);
                break;
            case 2:
                desk.setF2(sign);
                break;
            case 3:
                desk.setF3(sign);
                break;
            case 4:
                desk.setF4(sign);
                break;
            case 5:
                desk.setF5(sign);
                break;
            case 6:
                desk.setF6(sign);
                break;
            case 7:
                desk.setF7(sign);
                break;
            case 8:
                desk.setF8(sign);
                break;
            default:
                desk.setF9(sign);
        }

        deskRepository.save(desk);
    }

    public void save(Desk desk) {
        deskRepository.save(desk);
    }
}
