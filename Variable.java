public class Variable {
    Type type;
    int int_value;
    int[] obj_value;

    public Variable(Type type) {
        this.type = type;
        this.int_value = 0;
        this.obj_value = null;
    }

    public Variable getCopy() {
        Variable copy = new Variable(this.type);
        copy.int_value = this.int_value;
        copy.obj_value = this.obj_value;
        return copy;
    }

    public void incrementReferenceCount() {
        if (this.obj_value != null && ++this.obj_value[this.obj_value.length - 1] == 1)
            Executor.increaseNumObjects();
    }

    public void decrementReferenceCount() {
        if (this.obj_value != null && --this.obj_value[this.obj_value.length - 1] == 0)
            Executor.decreaseNumObjects();
    }
}
