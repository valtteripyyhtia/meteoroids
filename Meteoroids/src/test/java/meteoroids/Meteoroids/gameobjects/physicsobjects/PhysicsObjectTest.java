package meteoroids.Meteoroids.gameobjects.physicsobjects;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PhysicsObjectTest {

    PhysicsObject po;
    
    class A extends PhysicsObject {
        public A(double mass) {
            super(mass);
        }
    }
    
    @Before
    public void setUp() throws Exception {
        po = null;
    }

    @Test
    public void testNegativeMass() {
        po = new A(-1.0);
        assertEquals("Mass can't be smaller than 1.0.", 1.0, po.mass, 0.01);
    }
    
    @Test
    public void testZeroMass() {
        po = new A(0);
        assertEquals("Mass can't be zero.", 1.0, po.mass, 0.01);
    }
    
    @Test
    public void testSmallMass() {
        po = new A(0.52);
        assertEquals("Mass can't be smaller than 1.0.", 1.0, po.mass, 0.01);
    }
}