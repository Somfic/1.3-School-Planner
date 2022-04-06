package gui.simulation;

import data.Gender;
import data.Schedule;
import data.ScheduleItem;
import data.Teacher;
import io.FileManager;
import javafx.embed.swing.SwingFXUtils;
import logging.Logger;
import org.dyn4j.geometry.Vector2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeacherNpc extends Npc {

    private final Teacher teacher;

    public TeacherNpc(Teacher teacher) {
        super(teacher);
        this.teacher = teacher;

        try {
            BufferedImage sprite;
            if (teacher.getGender() == Gender.MALE) {
                sprite = ImageIO.read(FileManager.getResource("./npcSprites.png")).getSubimage(0, 0, 20, 34);
            } else {
                sprite = ImageIO.read(FileManager.getResource("./npcSprites.png")).getSubimage(20, 0, 21, 34);
            }
            setSprite(sprite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    void calculateTarget(Schedule schedule, int period, MapInfo mapInfo) {
        if(this.target != null) {
            return;
        }

        // Get the current period
        ScheduleItem currentPeriod = null;

        List<ScheduleItem> applicablePeriods = new ArrayList<>();

        for (ScheduleItem item : schedule.getItems()) {
            if (item.getTeacher().equals(teacher)) {
                applicablePeriods.add(item);
            }
        }

        for (ScheduleItem item : applicablePeriods) {
            if (item.getStartPeriod() <= period && item.getEndPeriod() >= period) {
                currentPeriod = item;
                break;
            }
        }

        if (currentPeriod == null) {
            if(applicablePeriods.size() > 0) {
                ScheduleItem firstPeriod = applicablePeriods.get(0);
                ScheduleItem lastPeriod = applicablePeriods.get(applicablePeriods.size() - 1);

                if(firstPeriod.getStartPeriod() > period || lastPeriod.getEndPeriod() < period) {
                    target = mapInfo.getSpawnPoints().get((int)(Math.floor(Math.random() * mapInfo.getSpawnPoints().size())));
                    return;
                }
            }

            // Not in a lesson, get a break area seat
            this.target = mapInfo.getTeacherArea().getSeat();

        } else {
            // Get a seat in the classroom
            this.target = mapInfo.getClassRoom(currentPeriod.getClassroom().getName()).getTeacherSeat();
        }
    }

    public Teacher getTeacher() {
        return teacher;
    }
}
