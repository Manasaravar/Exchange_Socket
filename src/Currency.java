public enum Currency {
    USD(92.5, "������"),
    EUR(99, "����"),
    RUB(1, "�����"),
    CHY(12.5, "����");


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
