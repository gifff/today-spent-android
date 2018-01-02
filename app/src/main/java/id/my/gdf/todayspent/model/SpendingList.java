package id.my.gdf.todayspent.model;

import java.util.List;

/**
 * Created by prime10 on 1/2/18.
 */

public class SpendingList {

    /**
     * spendings : [{"id":1,"user_id":1,"date":"2017-12-03T08:18:44Z","amount":500},{"id":26,"user_id":1,"date":"2017-12-19T08:18:44Z","amount":500},{"id":27,"user_id":1,"date":"0001-01-01T00:00:00Z","amount":500},{"id":28,"user_id":1,"date":"2017-12-29T08:18:44Z","amount":0},{"id":300,"user_id":1,"date":"2017-12-29T08:18:44Z","amount":0},{"id":302,"user_id":1,"date":"2017-12-29T08:18:44Z","amount":17000},{"id":303,"user_id":1,"date":"2017-12-29T08:18:44Z","amount":17000},{"id":304,"user_id":1,"date":"0001-01-01T00:00:00Z","amount":17000},{"id":305,"user_id":1,"date":"0001-01-01T00:00:00Z","amount":1800},{"id":306,"user_id":1,"date":"0001-01-01T00:00:00Z","amount":1800}]
     * statistics : {"total":56100,"count":10,"average":5610}
     */

    private StatisticsBean statistics;
    private List<SpendingsBean> spendings;

    public StatisticsBean getStatistics() {
        return statistics;
    }

    public void setStatistics(StatisticsBean statistics) {
        this.statistics = statistics;
    }

    public List<SpendingsBean> getSpendings() {
        return spendings;
    }

    public void setSpendings(List<SpendingsBean> spendings) {
        this.spendings = spendings;
    }

    public static class StatisticsBean {
        /**
         * total : 56100
         * count : 10
         * average : 5610
         */

        private int total;
        private int count;
        private int average;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getAverage() {
            return average;
        }

        public void setAverage(int average) {
            this.average = average;
        }
    }

    public static class SpendingsBean {
        /**
         * id : 1
         * user_id : 1
         * date : 2017-12-03T08:18:44Z
         * amount : 500
         */

        private int id;
        private int user_id;
        private String date;
        private int amount;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }
}
