package javabasic.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author wenchao.meng
 *         <p>
 *         Feb 08, 2018
 */
public class LongEventFactory implements EventFactory<LongEvent>
{
    public LongEvent newInstance()
    {
        return new LongEvent();
    }
}