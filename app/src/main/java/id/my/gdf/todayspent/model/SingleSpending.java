package id.my.gdf.todayspent.model;

/**
 * Created by prime10 on 12/3/17.
 */

public class SingleSpending {

    /**
     * spending : {"id":1,"user_id":1,"date":"0001-01-01T00:00:00Z","amount":0}
     */

    private SpendingBean spending;

    public SpendingBean getSpending() {
        return spending;
    }

    public void setSpending(SpendingBean spending) {
        this.spending = spending;
    }

    public static class SpendingBean {
        /**
         * id : 1
         * user_id : 1
         * date : 0001-01-01T00:00:00Z
         * amount : 0
         */

        private String id;
        private String user_id;
        private String date;
        private int amount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
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
