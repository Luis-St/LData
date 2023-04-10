package net.luis.data.common.io;

import net.luis.data.common.config.DataConfig;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public interface Writable<T extends Writer> {
	
	void write(@NotNull File file);
	
	void write(@NotNull T writer);
	
}
