package pl.jkk.listaspotkan;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class MeetingController {

    private MeetingRepository meetingRepository;

    public MeetingController(MeetingRepository meetingRepository){
        this.meetingRepository = meetingRepository;
    }

    @GetMapping("/")
    public String allMeetings(Model model){

        List<Meeting> allMeetings = meetingRepository.findAll();
        model.addAttribute("meetings", allMeetings);

        return "meetings";
    }

    @GetMapping("/meeting/{id}")
    public String showMeetingDetail(@PathVariable Long id, Model model){

        Optional<Meeting> optional = meetingRepository.findById(id);
        if(optional.isPresent()){
            Meeting meeting = optional.get();
            model.addAttribute("meeting", meeting);
            return "meeting-details";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/addmeeting")
    public String addMovieForm(Model model){
        model.addAttribute("meeting", new Meeting());
        Status[] status = Status.values();
        model.addAttribute("status", status);
        return "add-meeting";
    }

    @PostMapping("/addmeeting")
    public String addMeeting(Meeting meeting){
        meetingRepository.save(meeting);
        return "redirect:/";
    }
}
