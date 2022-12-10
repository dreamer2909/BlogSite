package example.blogsite.models;

public enum CategoryEnum {
    OPINION_ARGUMENT("Quan điểm - Tranh luận"),
    THINKING_OUT_LOUD("Thinking out loud"),
    SCIENCE_TECHNOLOGY("Khoa học - Công nghệ"),
    SPORT("Thể thao"),
    GAME("Game"),
    COMPOSE("Sáng tác"),
    BOOK("Sách"),
    MUSIC("Âm nhạc"),
    TRAVEL("Du lịch"),
    FASHION("Fashion"),
    FOOD("Ẩm thực"),
    PHOTOGRAPHY("Nhiếp ảnh")
    ;

    private String value;

    CategoryEnum(String s) {
        value = s;
    }

    public String toString() {
        return value;
    }
}
