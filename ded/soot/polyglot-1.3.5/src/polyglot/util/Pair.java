package polyglot.util;

/** A two-element tuple.
 */
public class Pair
{
    Object part1;
    Object part2;
    
    public Pair(Object p1, Object p2) {
	this.part1 = p1;
	this.part2 = p2;
    }
    
    public Object part1() {
	return part1;
    }
    
    public Object part2() {
	return part2;
    }
}
