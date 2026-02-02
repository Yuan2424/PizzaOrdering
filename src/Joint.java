public class Joint<T1, T2> {
    public T1 object1;
    public T2 object2;
    public Joint(T1 a, T2 b) {
        object1 = a;
        object2 = b;
    }
    public Joint<T1, T2> cloneJoint() {
        return new Joint<T1, T2>(this.object1, this.object2);
    }
}
