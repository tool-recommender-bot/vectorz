package mikera.vectorz.impl;

import static org.junit.Assert.*;
import mikera.indexz.Index;
import mikera.vectorz.AVector;
import mikera.vectorz.Vector;
import mikera.vectorz.Vectorz;

import org.junit.Test;

public class TestSparseHashedVector {

	@Test public void testConstruction() {
		SparseHashedVector sv=SparseHashedVector.create(10, Index.of(1,3,6), Vector.of(1.0,2.0,3.0));
		assertEquals(10,sv.length());
		assertEquals(3,sv.nonSparseValues().length());
		assertEquals(1.0,sv.get(1),0.0);
		assertEquals(0.0,sv.get(9),0.0);
		assertEquals(6.0,sv.elementSum(),0.0);
        assertTrue(sv.includesIndex(6));
        assertFalse(sv.includesIndex(5));
	}
	
	@Test public void testSet() {
		Vector v=Vector.of(-1,0,1);
		SparseHashedVector sv=SparseHashedVector.create(Vector.of(10,11,12));
		sv.set(v);
		assertEquals(v,sv);
	}
	
	@Test public void testAddProduct() {
		SparseHashedVector sv=SparseHashedVector.create(10, Index.of(1,3,6), Vector.of(1.0,2.0,3.0));
		
		AVector vz=Vectorz.newVector(10);
		AVector vs=Vector.of(0,1,2,3,4,5,6,7,8,9);
		
		assertTrue(vz instanceof AArrayVector);
		
		AVector v=vz.exactClone();
		v.addProduct(sv, vs);
		assertEquals(Vector.of(0,1,0,6,0,0,18,0,0,0),v);
		
		v.addProduct(sv, vs,2.0);
		assertEquals(Vector.of(0,3,0,18,0,0,54,0,0,0),v);

		AVector v2=Vectorz.newVector(20).subVector(5, 10);
		v2.addProduct(sv, vs);
		assertEquals(Vector.of(0,1,0,6,0,0,18,0,0,0),v2);
		
		v2.addProduct(sv, vs,2.0);
		assertEquals(Vector.of(0,3,0,18,0,0,54,0,0,0),v2);

		AVector v3=Vectorz.newVector(20).subVector(5, 10);
		
		v3.subVector(5,5).addProduct(sv,1, vs,1,1.0);
		assertEquals(Vector.of(0,0,0,0,0,1,0,6,0,0),v3);
		
		v3.subVector(5,5).addProduct(sv,1, vs,1,2.0);
		assertEquals(Vector.of(0,0,0,0,0,3,0,18,0,0),v3);

	}
}