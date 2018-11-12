package app.geoji.flower.apigameandroidtester.mock;

public class Data {
    public static final String BASE_URL = "http://localhost:3000";

    public static class UserData {
        private int id;
        private String token;
        private String name;
        private String fuid;

        private String email;
        private String password;

        public UserData(int id, String token, String name, String email, String password, String fuid) {
            this.id = id;
            this.token = token;
            this.name = name;
            this.email = email;
            this.password = password;
            this.fuid = fuid;
        }

        public int getId() {
            return id;
        }

        public String getToken() {
            return token;
        }

        public String getName() {
            return this.name;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getFuid() { return fuid; }
    }

    public static UserData[] USER_DATAS() {
        return new UserData[] {
                new UserData(
                        94968,
                        "hHtmEyhI-fd2f0b02-9b3e-4187-b1fc-ddf3ebe26fef",
                        "PUFFJames",
                        "gametest01@pufflive.me",
                        "puff1234",
                        "NcThJ8f0bgTA41aCWQchqKXHwOz2"
                ),
                new UserData(
                        94969,
                        "f3k4hRDZ-ac231f0a-625e-4814-a13c-e0429db7df22",
                        "PUFFJanice",
                        "gametest02@pufflive.me",
                        "puff1234",
                        "USQd7JHNukNC1N2hQ6vFmRKIJtJ2"
                ),
                new UserData(
                        94991,
                        "4FfqGlHR-a5cacc5f-8e2a-4309-865d-ece1699be8bb",
                        "PUFFJohn",
                        "gametest03@pufflive.me",
                        "puff1234",
                        "O11kuWGpWjPQAac2TuS7S3aUXwn1"
                ),
                new UserData(
                        94992,
                        "VIQHpPSn-3bf32101-eefb-4752-9cde-ce472b585d7c",
                        "PUFFJennifer",
                        "gametest04@pufflive.me",
                        "puff1234",
                        "UybOEGX8O8PqXAVawCa8VvLJLmZ2"
                ),
                new UserData(
                        94990,
                        "39DH4Cdl-bdaea497-0057-4b58-a7a4-fa28217e8ced",
                        "PUFFJonathan",
                        "gametest05@pufflive.me",
                        "puff1234",
                        "fvD8o7DGZgPSbUcKgryIh0O07wE3"
                )
        };
    }
}
