package model;

public class DCircularLinkedStructure {

	private int threshold;
	private Element first;
	private int elementCount;

	private static DCircularLinkedStructure circularStructure;

	public static DCircularLinkedStructure getInstance(int threshold) {
		if(circularStructure == null)
			return new DCircularLinkedStructure(threshold);

		if(circularStructure.getThreshold() != threshold)
			circularStructure.setThreshold(threshold);

		return circularStructure;
	}

	private DCircularLinkedStructure(int threshold) {
		this.threshold = threshold;
	}

	public int getThreshold() {
		return threshold;
	}

	public int getElementCount() {
		return elementCount;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public void addElement(int value) {
		if(threshold == elementCount)
			throw new IllegalArgumentException("Reached threshold limit.");

		Element newElement = new Element(value, null, null);
		if(first == null) {
			first = newElement;
			first.setPrevElement(newElement);
			first.setNextElement(newElement);
		} else {
			Element last = first.getPrevElement();
			newElement.setNextElement(first);
			newElement.setPrevElement(last);
			first.setPrevElement(newElement);
			last.setNextElement(newElement);
		}
		elementCount++;
	}
	
	
	public void removeElement(int value) {
		if(elementCount == 0)
			throw new IllegalArgumentException("No such element found.");

		if(first.getValue() == value) {
			remove(first);
			first = first.getNextElement();		
			if(elementCount == 0)
				first = null;
			return;
		}
		
		Element currentElement1 = first.getPrevElement();
		Element currentElement2 = first.getNextElement();
		boolean elementFound = false;
		for(int i=1;i<=elementCount/2;i++) {
			if(currentElement1.getValue() == value) {
				elementFound = true;
				remove(currentElement1);
				break;
			}
			currentElement1= currentElement1.getPrevElement();
			if(currentElement2.getValue() == value) {
				elementFound = true;
				remove(currentElement2);
				break;
			}
			currentElement2 = currentElement2.getNextElement();
		}

		if(!elementFound)
			throw new IllegalArgumentException("No such element found.");

	}	

	private void remove(Element current) {
		Element prevElement = current.getPrevElement();
		Element nextElement = current.getNextElement();
		prevElement.setNextElement(nextElement);
		nextElement.setPrevElement(prevElement);
		elementCount--;
	}
	
	
	/********************************************************************************************************************************/
	
	
	private static class Element {

		private int value;
		private Element prevElement;
		private Element nextElement;

		public Element(int value, Element prevElement, Element nextElement) {
			this.value = value;
			this.prevElement = prevElement;
			this.nextElement = nextElement;
		}

		public int getValue() {
			return value;
		}

		public Element getPrevElement() {
			return prevElement;
		}

		public void setPrevElement(Element prevElement) {
			this.prevElement = prevElement;
		}

		public Element getNextElement() {
			return nextElement;
		}

		public void setNextElement(Element nextElement) {
			this.nextElement = nextElement;
		}

	}
}
