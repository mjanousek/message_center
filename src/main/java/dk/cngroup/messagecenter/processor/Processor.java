package dk.cngroup.messagecenter.processor;

public interface Processor<T> {
	public T process(T t);
}
