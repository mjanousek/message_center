package dk.cngroup.messagecenter.service.processor;

public interface Processor<T> {
	public void process(T t);
}
