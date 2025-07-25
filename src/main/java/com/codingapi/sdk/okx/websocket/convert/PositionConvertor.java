package com.codingapi.sdk.okx.websocket.convert;

import com.codingapi.sdk.okx.websocket.dto.Position;
import com.codingapi.sdk.okx.websocket.dto.vo.OrderAlgo;
import com.codingapi.sdk.okx.websocket.protocol.answer.PositionsAnswer;

import java.util.stream.Collectors;

public class PositionConvertor {

    /**
     * 仓位简化数据
     */
    public static Position convert(PositionsAnswer answer){
        if(answer==null){
            return null;
        }

        return new Position(
                Integer.parseInt(answer.getLever()),
                getPosUsdt(answer),
                Float.parseFloat(answer.getUpl()),
                getUplRatioValue(answer),
                Float.parseFloat(answer.getAvgPx()),
                Float.parseFloat(answer.getMargin()),
                Float.parseFloat(answer.getMgnRatio()),
                Float.parseFloat(answer.getLiqPx()),
                Float.parseFloat(answer.getMarkPx()),
                answer.getCloseOrderAlgo().stream()
                        .map(PositionConvertor::convert)
                        .collect(Collectors.toList())
        );
    }


    public static OrderAlgo convert(PositionsAnswer.CloseOrderAlgo closeOrderAlgo){
        OrderAlgo orderAlgo = new OrderAlgo();
        orderAlgo.setAlgoId(closeOrderAlgo.getAlgoId());
        orderAlgo.setCloseFraction(closeOrderAlgo.getCloseFraction());

        orderAlgo.setSlTriggerPx(closeOrderAlgo.getSlTriggerPx());
        orderAlgo.setSlTriggerPxType(closeOrderAlgo.getSlTriggerPxType());
        orderAlgo.setTpTriggerPx(closeOrderAlgo.getTpTriggerPx());
        orderAlgo.setTpTriggerPxType(closeOrderAlgo.getTpTriggerPxType());
        return orderAlgo;
    }


    private static float getPosUsdt(PositionsAnswer answer){
        return (float) (Float.parseFloat(answer.getLast())/100.0/Float.parseFloat(answer.getLever()) * Float.parseFloat(answer.getPos()));
    }

    private static float getUplRatioValue(PositionsAnswer answer){
        return (float) (Float.parseFloat(answer.getUplRatio()) * 100.0);
    }

    private static float getMgnRatioValue(PositionsAnswer answer){
        return (float) (Float.parseFloat(answer.getMgnRatio()) * 100.0);
    }
}
