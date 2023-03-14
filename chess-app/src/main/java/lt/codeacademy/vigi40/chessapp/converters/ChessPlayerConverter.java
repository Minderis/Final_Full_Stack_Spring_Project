package lt.codeacademy.vigi40.chessapp.converters;

import lt.codeacademy.vigi40.chessapp.dto.AddChessPlayerDTO;
import lt.codeacademy.vigi40.chessapp.dto.ChessPlayerDTO;
import lt.codeacademy.vigi40.chessapp.dto.EditChessPlayerDTO;
import lt.codeacademy.vigi40.chessapp.entities.ChessPlayer;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ChessPlayerConverter {

    public static ChessPlayerDTO convertChessPlayerEntityToDto(ChessPlayer chessPlayer) {
        ChessPlayerDTO chessPlayerDTO = null;
        if (chessPlayer != null) {
            chessPlayerDTO = new ChessPlayerDTO();
            chessPlayerDTO.setId(chessPlayer.getId());
            chessPlayerDTO.setName(chessPlayer.getName());
            chessPlayerDTO.setSurname(chessPlayer.getSurname());
            String emailCandidate = chessPlayer.getEmail();
            if (isValidEmail(emailCandidate)) {
                chessPlayerDTO.setEmail(emailCandidate);
            } else {
                chessPlayerDTO.setEmail("User has invalid email");
            }
            try {
                chessPlayerDTO.setSex(getSexFromPersonCode(chessPlayer.getPersonCode()));
            } catch (IllegalArgumentException e) {
                chessPlayerDTO.setSex("Can not determine the gender from invalid person code");
            }
            try {
                chessPlayerDTO.setAge(Integer.toString(getAgeFromPersonCode(chessPlayer.getPersonCode())));
            } catch (IllegalArgumentException e) {
                chessPlayerDTO.setAge("Can not determine age from invalid person code");
            }
            try {
                Period duration = getChessPlayDuration(chessPlayer.getStartPlayChessFromDate());
                int years = duration.getYears();
                int months = duration.getMonths();
                String output;
                String yearString = years == 1 ? "year" : "years";
                String monthString = months == 1 ? "month" : "months";
                if (months == 0) {
                    output = String.format("%d %s", years, yearString);
                } else {
                    output = String.format("%d %s and %d %s", years, yearString, months, monthString);
                }
                chessPlayerDTO.setDuration(output);
            } catch (IllegalArgumentException e) {
                chessPlayerDTO.setDuration("Playing chess start date is set later than today date.");
            }
        }
        return chessPlayerDTO;
    }

    public static EditChessPlayerDTO convertChessPlayerEntityToEditDto(ChessPlayer chessPlayer) {
        EditChessPlayerDTO editChessPlayerDTO = null;
        if (chessPlayer != null) {
            editChessPlayerDTO = new EditChessPlayerDTO();
            editChessPlayerDTO.setId(chessPlayer.getId());
            editChessPlayerDTO.setName(chessPlayer.getName());
            editChessPlayerDTO.setSurname(chessPlayer.getSurname());
            editChessPlayerDTO.setEmail(chessPlayer.getEmail());
            editChessPlayerDTO.setPersonCode("***********");
            editChessPlayerDTO.setStartPlayChessFromDate(chessPlayer.getStartPlayChessFromDate());
        }
        return editChessPlayerDTO;
    }

    public static List<ChessPlayerDTO> convertChessPLayerEntityListToDto(List<ChessPlayer> chessPlayerList) {
        List<ChessPlayerDTO> chessPlayerDTOList = null;
        for (ChessPlayer chessPlayer : chessPlayerList) {
            if (chessPlayerDTOList == null) {
                chessPlayerDTOList = new ArrayList<>();
            }
            chessPlayerDTOList.add(ChessPlayerConverter.convertChessPlayerEntityToDto(chessPlayer));
        }
        return chessPlayerDTOList;
    }

    public static ChessPlayer convertAddChessPlayerDtoToEntity(AddChessPlayerDTO addChessPlayerDTO) {
        ChessPlayer chessPlayer = null;
        if (addChessPlayerDTO != null) {
            if (addChessPlayerDTO.getName() == null || !addChessPlayerDTO.getName().
                    matches("^[A-Za-z\\-]{2,}$")) {
                throw new IllegalArgumentException(
                        "Name must be at least 2 characters long and contain only letters and hyphen");
            }
            if (addChessPlayerDTO.getSurname() == null || !addChessPlayerDTO.getSurname().
                    matches("^[A-Za-z\\-]{2,}$")) {
                throw new IllegalArgumentException("" +
                        "Surname must be at least 2 characters long and contain only letters and hyphen");
            }
            if (addChessPlayerDTO.getEmail() == null || !addChessPlayerDTO.getEmail().
                    matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                throw new IllegalArgumentException("Invalid email format");
            }
            if (addChessPlayerDTO.getPersonCode() == null
                    || !isValidPersonCode(addChessPlayerDTO.getPersonCode().toString())) {
                throw new IllegalArgumentException("Person code should meet LT requirements");
            }
            if (addChessPlayerDTO.getStartPlayChessFromDate() == null
                    || addChessPlayerDTO.getStartPlayChessFromDate().compareTo(LocalDate.now()) > 0) {
                throw new IllegalArgumentException("Start play chess from date cannot be empty or upcoming date");
            }
            chessPlayer = new ChessPlayer();
            chessPlayer.setName(addChessPlayerDTO.getName().toUpperCase());
            chessPlayer.setSurname(addChessPlayerDTO.getSurname().toUpperCase());
            chessPlayer.setEmail(addChessPlayerDTO.getEmail().toLowerCase());
            chessPlayer.setPersonCode(addChessPlayerDTO.getPersonCode());
            chessPlayer.setStartPlayChessFromDate(addChessPlayerDTO.getStartPlayChessFromDate());
        }
        return chessPlayer;
    }

    public static ChessPlayer convertAddChessPlayerDtoToEntityForEditRecord(AddChessPlayerDTO addChessPlayerDTO) {
        ChessPlayer chessPlayer = null;
        if (addChessPlayerDTO != null) {
            if (addChessPlayerDTO.getName() != null && !addChessPlayerDTO.getName().
                    matches("^[A-Za-z\\-]{2,}$")) {
                throw new IllegalArgumentException(
                        "Name must be at least 2 characters long and contain only letters and hyphen");
            }
            if (addChessPlayerDTO.getSurname() != null && !addChessPlayerDTO.getSurname().
                    matches("^[A-Za-z\\-]{2,}$")) {
                throw new IllegalArgumentException("" +
                        "Surname must be at least 2 characters long and contain only letters and hyphen");
            }
            if (addChessPlayerDTO.getEmail() != null && !addChessPlayerDTO.getEmail().
                    matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                throw new IllegalArgumentException("Invalid email format");
            }
            if (addChessPlayerDTO.getPersonCode() != null
                    && !isValidPersonCode(addChessPlayerDTO.getPersonCode().toString())) {
                throw new IllegalArgumentException("Person code should meet LT requirements");
            }
            if (addChessPlayerDTO.getStartPlayChessFromDate() != null
                    && addChessPlayerDTO.getStartPlayChessFromDate().compareTo(LocalDate.now()) > 0) {
                throw new IllegalArgumentException("Start play chess from date cannot be empty or upcoming date");
            }
            chessPlayer = new ChessPlayer();
            chessPlayer.setName(addChessPlayerDTO.getName() != null
                    ?  addChessPlayerDTO.getName().toUpperCase() : null);
            chessPlayer.setSurname(addChessPlayerDTO.getSurname() != null
                    ? addChessPlayerDTO.getSurname().toUpperCase() : null);
            chessPlayer.setEmail(addChessPlayerDTO.getEmail() != null
                    ? addChessPlayerDTO.getEmail().toLowerCase() : null);
            chessPlayer.setPersonCode(addChessPlayerDTO.getPersonCode());
            chessPlayer.setStartPlayChessFromDate(addChessPlayerDTO.getStartPlayChessFromDate());
        }
        return chessPlayer;
    }

    private static String getSexFromPersonCode(Long personCode) {
        int firstDigit = (int) (personCode / 10000000000L); // extract first digit
        if (firstDigit == 3 || firstDigit == 5) {
            return "male";
        } else if (firstDigit == 4 || firstDigit == 6) {
            return "female";
        } else {
            throw new IllegalArgumentException("Invalid person code");
        }
    }

    private static int getAgeFromPersonCode(Long personCode) {
        int year;
        int firstDigit = (int) (personCode / 10000000000L); // extract first digit
        int secondAndThirdDigits = (int) (personCode / 100000000L) % 100; // extract second and third digits
        if (firstDigit == 3 || firstDigit == 4) {
            year = 1900 + secondAndThirdDigits;
        } else if ((firstDigit == 5 || firstDigit == 6) && secondAndThirdDigits <= LocalDate.now().getYear() % 100) {
            year = 2000 + secondAndThirdDigits;
        } else {
            throw new IllegalArgumentException("Invalid person code");
        }
        int month = (int) (personCode / 1000000L) % 100;
        int day = (int) (personCode / 10000L) % 100;
        if ((month > 0 && month < 13) && (day > 0 && day < 32)) {
            // Check if the year is a leap year
            boolean isLeapYear = Year.of(year).isLeap();
            // Check if the day is valid for the given month and year
            boolean isValidDay = day <= Month.of(month).length(isLeapYear);
            if (isValidDay) {
                LocalDate birthDate = LocalDate.of(year, month, day);
                LocalDate now = LocalDate.now();
                Period age = Period.between(birthDate, now);
                return age.getYears();
            } else {
                throw new IllegalArgumentException("Invalid person code");
            }
        } else {
            throw new IllegalArgumentException("Invalid person code");
        }
    }

    private static Period getChessPlayDuration(LocalDate startDate) {
        LocalDate currentDate = LocalDate.now();
        if (startDate.compareTo(currentDate) > 0) {
            throw new IllegalArgumentException("start date must be before or the same as today date");
        } else {
            return Period.between(startDate, currentDate);
        }
    }

    private static boolean isValidEmail(String email) {
        String emailRegexp = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern emailPattern = Pattern.compile(emailRegexp);
        if (email == null) {
            return false;
        }
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    private static boolean isValidPersonCode(String personCode) {
        if (personCode == null || personCode.length() != 11) {
            return false;
        }
        int sex = Character.getNumericValue(personCode.charAt(0));
        if (sex < 3 || sex > 6) {
            return false;
        }
        int year = Integer.parseInt(personCode.substring(1, 3));
        int currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100;
        if ((sex < 5 && (year < 0 || year > 99)) || (sex >= 5 && (year < 0 || year > currentYear))) {
            return false;
        }
        int month = Integer.parseInt(personCode.substring(3, 5));
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        if (month < 1 || (year == currentYear && month > currentMonth) || month > 12) {
            return false;
        }
        int nm = Integer.parseInt(personCode.substring(5, 7));
        int currentDay = Calendar.getInstance().get(Calendar.DATE);
        boolean isLeapYear = ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
        int maxDaysInMonth;
        if (month == 2) {
            if (isLeapYear) {
                maxDaysInMonth = 29;
            } else {
                maxDaysInMonth = 28;
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            maxDaysInMonth = 30;
        } else {
            maxDaysInMonth = 31;
        }
        if (nm < 1 || (year == currentYear && month == currentMonth && nm > currentDay) || nm > maxDaysInMonth) {
            return false;
        }
        String fourDigits = personCode.substring(7);
        for (int i = 0; i < fourDigits.length(); i++) {
            if (!Character.isDigit(fourDigits.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
