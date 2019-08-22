package lab.s2jh.core.service;


import java.util.concurrent.TimeUnit;

public interface IDistributedLock {

	boolean tryLock(String key);

	boolean tryLock(String key, long timeout, TimeUnit unit);

	void lock(String key);

	void unLock(String key);

}
