package com.artsafin.tgalarm.bot.routing;

import com.artsafin.tgalarm.bot.user.UserSession;
import com.artsafin.tgalarm.bot.command.Command;
import com.artsafin.tgalarm.bot.command.DeleteAlarmCommand;
import com.artsafin.tgalarm.bot.command.SetEditFlowCommand;
import com.google.gson.*;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CallbackQueryRouter implements Router, ChainableRouter {
    private Router successor;

    @Override
    public Command route(Update update, UserSession session) throws UnprocessableUpdateException {
        if (!update.hasCallbackQuery()) {
            return passToSuccessor(update, session);
        }

        final CallbackQuery callbackQuery = update.getCallbackQuery();

        JsonObject json;

        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(callbackQuery.getData());
            json = jsonElement.getAsJsonObject();

        } catch (JsonParseException | IllegalStateException | NullPointerException e) {
            throw new UnprocessableUpdateException("Callback query data could not be parsed", e);
        }

        if (!json.has("action") || !json.has("alarmId")) {
            throw new UnprocessableUpdateException("Invalid format of callback query (missing action and/or alarmId keys)");
        }

        String alarmId = json.get("alarmId").getAsString();

        switch (json.get("action").getAsString()) {
            case "edit":
                return new SetEditFlowCommand(alarmId);
            case "delete":
                return new DeleteAlarmCommand(alarmId);
        }

        throw new UnprocessableUpdateException("Unsupported callback query action");
    }

    @Override
    public Router setSuccessor(Router successor) {
        this.successor = successor;
        return this;
    }

    @Override
    public Router getSuccessor() {
        return successor;
    }
}
