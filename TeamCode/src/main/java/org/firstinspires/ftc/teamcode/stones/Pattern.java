package org.firstinspires.ftc.teamcode.stones;

public enum Pattern {
    FIRST_FOURTH {
        public int getPattern() {
            return 1;
        }
    },
    SECOND_FIFTH {
        public int getPattern() {
            return 2;
        }
    },
    THIRD_SIXTH {
        public int getPattern() {
            return 3;
        }
    };

    public abstract int getPattern();
}
