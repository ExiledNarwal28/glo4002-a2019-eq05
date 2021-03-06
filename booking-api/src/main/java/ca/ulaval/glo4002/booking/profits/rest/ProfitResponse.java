package ca.ulaval.glo4002.booking.profits.rest;

public class ProfitResponse {

    private Float in;
    private Float out;
    private Float profit;

    public ProfitResponse(float in, float out, float profit) {
        this.in = in;
        this.out = out;
        this.profit = profit;
    }

    public float getIn() {
        return in;
    }

    public float getOut() {
        return out;
    }

    public float getProfit() {
        return profit;
    }
}
