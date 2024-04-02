public enum Currency {
    USD(92.5, "Доллар"),
    EUR(99, "ЕВРО"),
    RUB(1, "Рубль"),
    CHY(12.5, "Юань");


    Currency(double course, String nameCurrency) {
        this.course = course;
        this.nameCurrency = nameCurrency;
    }

    public double getCourse() {
        return course;
    }

    public String getNameCurrency() {
        return nameCurrency;
    }

    private final double course;
    private final String nameCurrency;
}
