package cn.night.fuo.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class FuoExtendEnums<TCode> extends Enum<TCode> {

    private TCode code;
    private String desc;

    /**
     * Sole constructor.  Programmers cannot invoke this constructor.
     * It is for use by code emitted by the compiler in response to
     * enum type declarations.
     *
     * @param name    - The name of this enum constant, which is the identifier
     *                used to declare it.
     * @param ordinal - The ordinal of this enumeration constant (its position
     *                in the enum declaration, where the initial constant is assigned
     */
    protected FuoExtendEnums(String name, int ordinal) {
        super(name, ordinal);
    }

    public final TCode getCode(){
        return this.code;
    }

    public final String getDesc(){
        return this.desc;
    }

    @Override
    public String toString() {
        return this.code.toString();
    }


    public  <T extends FuoExtendEnums>  T getByCode<T>(String code) {
        if (StringUtil.isBlank(code)) {
            return null;
        }
        for (JDCustomerLevelEm temp : JDCustomerLevelEm.values()) {
            if (code.equals(temp.getCode())) {
                return temp;
            }
        }
        return null;
    }
}
