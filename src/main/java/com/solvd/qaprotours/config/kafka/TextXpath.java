package com.solvd.qaprotours.config.kafka;

import com.jcabi.xml.XML;
import lombok.RequiredArgsConstructor;

/**
 * @author Lisov Ilya
 */
@RequiredArgsConstructor
public final class TextXpath {

    private final XML xml;
    private final String node;

    @Override
    public String toString() {
        return this.xml.nodes(this.node)
                .get(0)
                .xpath("text()")
                .get(0);
    }

}
