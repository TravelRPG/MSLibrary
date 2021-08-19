package kr.msleague.mslibrary.customitem.impl.database;

import java.io.File;
import java.io.IOException;

public interface FileSerializer<T> {

    T read(File file) throws IOException;

    void write(File file, T t) throws IOException;
}
