package com.example.project1.BLL;

import java.util.*;
public class FirstAid {

    private static final Map<String, String> knowledgeBase = new HashMap<>();

    static {
        knowledgeBase.put("injured", "Could you provide more details about the injury or the situation?");
        knowledgeBase.put("road accident", "Please ensure your safety first. Check the animal for visible injuries like bleeding or fractures.\n Here's a video for help: https://www.youtube.com/watch?v=-k83G2VY2Rw. Is there any specific injury?");
        knowledgeBase.put("bleeding", "Apply gentle pressure to stop the bleeding using a clean cloth or bandage. Avoid using a tourniquet.\n Here's a video for guidance: https://www.youtube.com/watch?v=LnO-gQjHuiw");
        knowledgeBase.put("fracture", "Immobilize the limb with a splint. Ensure the animal doesn't move unnecessarily.\n Here's a video for assistance: https://www.youtube.com/watch?v=xvFFNe842yY");
        knowledgeBase.put("broken", knowledgeBase.get("fracture"));
        knowledgeBase.put("choking", "Ensure the airway is clear by opening the mouth carefully.\n For a choking dog, this video might help: https://www.youtube.com/watch?v=gT_vNktCbyw");
        knowledgeBase.put("unconscious", "Check if the animal is breathing or if you can feel a pulse. If the gums are blue, it may need CPR.\n Dog CPR: https://www.youtube.com/watch?v=iaUz8-jKCys | Cat CPR: https://www.youtube.com/watch?v=vAqAxdhPFA8");
        knowledgeBase.put("pulse", "To check a dog's pulse, locate the femoral artery inside the thigh. TRP video for guidance: https://www.youtube.com/watch?v=h5CQoy5O0sw");
        knowledgeBase.put("respiration", knowledgeBase.get("pulse"));
        knowledgeBase.put("temperature", knowledgeBase.get("pulse"));
        knowledgeBase.put("breathing", knowledgeBase.get("unconscious"));
        knowledgeBase.put("heatstroke", "Symptoms include excessive panting, bright red gums, and staggering. Cool the animal with water (not ice-cold).\n Here's a video for guidance: Cat: https://www.youtube.com/watch?v=NmC4d_53WJg | Dog: https://www.youtube.com/watch?v=IKoNYxsWqk0");
        knowledgeBase.put("move injured animal", "Use a stretcher or a firm board to keep the animal stable. Avoid bending the spine.\n Here's a video for help: https://www.youtube.com/watch?v=N0SK_Iw-wcI");
        knowledgeBase.put("burn", "Flush the burn area with cool water for 10 minutes. Do not use ointments.\n Here's a video: https://www.youtube.com/watch?v=DJVS-lBhw44");
        knowledgeBase.put("scald", knowledgeBase.get("burn"));
        knowledgeBase.put("seizure", "Keep the animal safe by moving objects away. Don't touch the animal during a seizure.\n Here's a helpful video: https://www.youtube.com/watch?v=N-EOBSFYU1U");
        knowledgeBase.put("eye injury", "Avoid touching the eye. Keep it covered with a clean cloth.\n Here's a video for assistance: https://www.youtube.com/watch?v=SEHG-MDJ270");
    }

    public String getResponse(String query) {
        for (String key : knowledgeBase.keySet()) {
            if (query.toLowerCase().contains(key)) {
                return knowledgeBase.get(key);
            }
        }
        if (query.matches("(?i).*\\b(hi|hello|hey)\\b.*")) {
            return "Hello! How can I assist you ?";
        } else if (query.matches("(?i).*\\b(thank you|thanks|appreciate it)\\b.*")) {
            return "You're welcome! Do you have any more questions or would you like to exit?";
        } else if (query.matches("(?i).*\\b(bye|goodbye|see you)\\b.*")) {
            return "Goodbye! Take care, and stay safe!";
        } else if (query.matches("(?i).*\\b(how are you|what's up)\\b.*")) {
            return "I'm just a chatbot, but I'm here to help! How can I assist you today?";
        }
        return "I'm sorry, I couldn't find information for that. Could you provide more details?";
    }
}
