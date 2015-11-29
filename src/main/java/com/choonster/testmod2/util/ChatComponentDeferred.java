package com.choonster.testmod2.util;

import com.choonster.testmod2.Logger;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

/**
 * A chat component that's created by calling a {@link Supplier}&lt;{@link IChatComponent}&gt; every time it's needed.
 */
public class ChatComponentDeferred implements IChatComponent {
	private final Supplier<IChatComponent> factory;

	private ChatComponentDeferred(Supplier<IChatComponent> factory) {
		this.factory = factory;
	}

	public static ChatComponentDeferred create(Supplier<IChatComponent> factory) {
		return new ChatComponentDeferred(factory);
	}

	private IChatComponent getChatComponent() {
		try {
			IChatComponent chatComponent = factory.get();
			Logger.info("ChatComponentDeferred resolved! %s", chatComponent.getFormattedText());
			return chatComponent;
		} catch (Throwable e) {
			return new ChatComponentTranslation("message.chatComponentDeferred.error", e);
		}
	}

	@Override
	public IChatComponent setChatStyle(ChatStyle chatStyle) {
		return getChatComponent().setChatStyle(chatStyle);
	}

	@Override
	public ChatStyle getChatStyle() {
		return getChatComponent().getChatStyle();
	}

	@Override
	public IChatComponent appendText(String text) {
		return getChatComponent().appendText(text);
	}

	@Override
	public IChatComponent appendSibling(IChatComponent sibling) {
		return getChatComponent().appendSibling(sibling);
	}

	@Override
	public String getUnformattedTextForChat() {
		return getChatComponent().getUnformattedTextForChat();
	}

	@Override
	public String getUnformattedText() {
		return getChatComponent().getUnformattedText();
	}

	@Override
	public String getFormattedText() {
		return getChatComponent().getFormattedText();
	}

	@Override
	public List getSiblings() {
		return getChatComponent().getSiblings();
	}

	@Override
	public IChatComponent createCopy() {
		return getChatComponent().createCopy();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<IChatComponent> iterator() {
		return getChatComponent().iterator();
	}

	@Override
	public String toString() {
		return "ChatComponentDeferred{ " + getChatComponent().toString() + " }";
	}
}
