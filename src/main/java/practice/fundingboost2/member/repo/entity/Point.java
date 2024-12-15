package practice.fundingboost2.member.repo.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;

@Getter
@Embeddable
@NoArgsConstructor
public class Point {

    public final static Point ZERO = new Point(0);

    private int point;

    public Point(int point) {
        validate(point);
        this.point = point;
    }

    private static void validate(int point) {
        if (point < 0) {
            throw new CommonException(ErrorCode.INVALID_POINT_LACK);
        }
    }

    public Point add(Point point) {
        return new Point(this.point + point.point);
    }

    public Point subtract(Point point) {
        return new Point(this.point - point.point);
    }
}
